(ns clj-kraken-api.client
  (:require [aleph.http :as http]
            [aleph.http.client-middleware :as cm]
            [manifold.deferred :as d]
            [pandect.algo.sha512 :as sha512]
            [pandect.algo.sha256 :as sha256]
            [clojure.data.codec.base64 :as b64]
            [cheshire.core :as json])
  (:import [java.io InputStream BufferedReader InputStreamReader]))

(def ^:private kraken-api "https://api.kraken.com")
(def ^:private kraken-api-version 0)

;; API-Key = API key
;; API-Sign = Message signature using HMAC-SHA512 of
;;   (URI path + SHA256(nonce + POST data)) and base64 decoded secret API key
(defn- sign-request [api-key secret-key path nonce data]
  (let [secret-bytes (b64/decode (.getBytes secret-key))
        nd-digest (sha256/sha256-bytes (str nonce data))
        path-digest (-> (str "/" kraken-api-version path)
                        (.getBytes)
                        (concat nd-digest)
                        byte-array)
        hmac-bytes (sha512/sha512-hmac-bytes path-digest secret-bytes)
        signature (String. (b64/encode hmac-bytes))]
    {"API-KEY" api-key "API-Sign" signature}))

(defn- input-stream-to-buffered-reader [is]
  {:pre [(instance? InputStream is)]}
  (new BufferedReader (new InputStreamReader is)))

(defn- parse-json-input-stream [is]
  {:pre [(instance? InputStream is)]}
  (-> (input-stream-to-buffered-reader is)
      (json/parse-stream keyword)))

(defn- parse-json-body-in-res [res]
  {:pre [(d/deferred? res)]}
  (-> res
      (d/chain #(update % :body parse-json-input-stream))
      (d/catch (fn [e]
                 (let [edata (ex-data e)]
                   (if (and (some? edata) (instance? InputStream (:body edata)))
                     (update edata :body parse-json-input-stream)
                     (throw e)))))))

(defn- request [{:keys [path data headers]}]
  (-> (http/post (format "%s/%d%s" kraken-api kraken-api-version path)
                 {:headers headers
                  :body data})
      parse-json-body-in-res
      ;(d/chain #(:body %))
      ))

(defn private-request [{:keys [api-key api-secret]} {:keys [path data]}]
  (let [nonce (str (System/currentTimeMillis))
        qs-data (-> (or data {})
                    (assoc :nonce nonce)
                    cm/generate-query-string)
        headers (sign-request api-key api-secret path nonce qs-data)]
    (request {:path path
              :headers headers
              :data qs-data})))

(defn public-request [{:keys [path data]}]
  (let [qs-data (cm/generate-query-string data)]
    (request {:path path
              :data qs-data})))
