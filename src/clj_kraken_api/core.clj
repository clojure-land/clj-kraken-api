(ns clj-kraken-api.core
  (:require [clojure.spec.alpha :as s]
            [clj-kraken-api.client :as client]
            [clj-kraken-api.spec :as spec]))

;; Public market data

(defn server-time []
  (client/public-request {:path "/public/Time"}))

(defn asset-info 
  ([] (asset-info nil))
  ([params]
   {:pre [(s/valid? ::spec/asset-info-params params)]}
   (client/public-request {:path "/public/Assets"
                           :data params})))

(defn asset-pairs 
  ([] (asset-pairs nil))
  ([params]
   {:pre [(s/valid? ::spec/asset-pairs-params params)]}
   (client/public-request {:path "/public/AssetPairs"
                           :data params})))

(defn ticker [params]
  {:pre [(s/valid? ::spec/ticker-params params)]}
  (client/public-request {:path "/public/Ticker"
                          :data params}))

(defn ohlc [params]
  {:pre [(s/valid? ::spec/ohlc-params params)]}
  (client/public-request {:path "/public/OHLC"
                          :data params}))

(defn order-book [params]
  {:pre [(s/valid? ::spec/order-book-params params)]}
  (client/public-request {:path "/public/Depth"
                          :data params}))

(defn recent-trades [params]
  {:pre [(s/valid? ::spec/recent-trades-params params)]}
  (client/public-request {:path "/public/Trades"
                          :data params}))

(defn recent-spread [params]
  {:pre [(s/valid? ::spec/recent-spread-params params)]}
  (client/public-request {:path "/public/Spread"
                          :data params}))

;; Private user data 

(defn account-balance [cred]
  (client/private-request cred {:path "/private/Balance"}))

(defn trade-balance 
  ([cred] (trade-balance cred nil))
  ([cred params]
   {:pre [(s/valid? ::spec/trade-balance-params params)]}
   (client/private-request cred
                           {:path "/private/TradeBalance"
                            :data params})))

(defn open-orders
  ([cred] (trade-balance cred nil))
  ([cred params]
   {:pre [(s/valid? ::spec/open-orders-params params)]}
   (client/private-request cred
                           {:path "/private/OpenOrders"
                            :data params})))

(defn closed-orders 
  ([cred] (closed-orders cred nil)) 
  ([cred params]
   {:pre [(s/valid? ::spec/closed-orders-params params)]}
   (client/private-request cred
                           {:path "/private/ClosedOrders"
                            :data params})))

(defn orders-info [cred params]
  {:pre [(s/valid? ::spec/orders-info-params params)]}
  (client/private-request cred
                          {:path "/private/QueryOrders"
                           :data params}))

(defn trades-history 
  ([cred] (trades-history cred nil))
  ([cred params] 
   {:pre [(s/valid? ::spec/trades-history-params params)]}
   (client/private-request cred
                           {:path "/private/TradesHistory"})))

(defn trades-info [cred params]
  {:pre [(s/valid? ::spec/trades-info-params params)]}
  (client/private-request cred
                          {:path "/private/QueryTrades"
                           :data params}))

(defn open-positions [cred params]
  {:pre [(s/valid? ::spec/open-positions-params params)]}
  (client/private-request cred
                          {:path "/private/OpenPositions"
                           :data params}))

(defn ledgers-info 
  ([cred] (ledgers-info cred nil))
  ([cred params]
   {:pre [(s/valid? ::spec/ledgers-info-params)]}
   (client/private-request cred
                           {:path "/private/Ledgers"
                            :data params})))

(defn ledgers [cred params]
  {:pre [(s/valid? ::spec/ledgers-params)]}
  (client/private-request cred
                          {:path "/private/QueryLedgers"
                           :data params}))

(defn trade-volume 
  ([cred] (trade-volume cred nil)) 
  ([cred params] 
   {:pre [(s/valid? ::spec/trades-volume-params params)]}
   (client/private-request cred
                           {:path "/private/TradeVolume"
                            :data params})))

;; Private user trading

(defn add-order [cred params]
  (client/private-request cred
                          {:path "/private/AddOrder"
                           :data params}))

(defn cancel-order [cred params]
  (client/private-request cred
                          {:path "/private/CancelOrder"
                           :data params}))
