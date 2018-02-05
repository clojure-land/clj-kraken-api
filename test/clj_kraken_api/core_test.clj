(ns clj-kraken-api.core-test
  (:require [clj-kraken-api.core :as kraken-api]))

(def KRAKEN_API_KEY "test")     
(def KRAKEN_API_SECRET "test")
  
(def cred {:api-key KRAKEN_API_KEY
           :api-secret KRAKEN_API_SECRET})

@(kraken-api/server-time)
@(kraken-api/account-balance {:})
