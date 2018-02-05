(ns clj-kraken-api.spec
  (:require [clojure.spec.alpha :as s]))

(s/def ::aclass string?)
(s/def :single/asset string?)
(s/def :multiple/asset (s/coll-of string?))
(s/def :single/pair string?)
(s/def :multiple/pair (s/coll-of string?))
(s/def ::since string?)

(s/def :asset-info/info string?)
(s/def ::asset-info-params 
  (s/or :params (s/keys :opt-un [:asset-info/info
                                 ::aclass
                                 :multiple/asset])
        :nil nil?))

(s/def :asset-pairs/info #{"info" "leverage" "fees" "margin"})

(s/def ::asset-pairs-params 
  (s/or :params (s/keys :opt-un [:asset-pairs/info
                                 :multiple/pair])
        :nil nil?))

(s/def ::ticker-params (s/keys :req-un [:multiple/pair]))

(s/def :ohlc/interval #{1, 5, 15, 30, 60, 240, 1440, 10080, 21600})
(s/def ::ohlc-params (s/keys :req-un [:single/pair]
                             :opt-un [:ohlc/interval
                                      ::since]))

(s/def :order-book/count (s/and number? pos?))
(s/def ::order-book-params (s/keys :req-un [:single/pair]
                                   :opt-un [:order-book/count]))

(s/def ::recent-trades-params (s/keys :req-un [:single/pair]
                                      :opt-un [::since]))

(s/def ::recent-spread-params (s/merge ::recent-trades-params))

(s/def ::trade-balance-params 
  (s/or :params (s/keys :opt-un [::aclass
                                 :single/asset])
        :nil nil?))

(s/def ::trades boolean?)
(s/def ::userref string?)
(s/def ::open-orders-params
  (s/or :params (s/keys :opt-un [::trades
                                 ::userref])
        :nil nil?))

(s/def ::start string?)
(s/def ::end string?)
(s/def ::ofs string?)
(s/def ::closetime #{"open" "close" "both"})
(s/def ::closed-orders-params
  (s/or :params (s/keys :opt-un [::trades
                                 ::userref
                                 ::start
                                 ::end
                                 ::ofs
                                 ::closetime])
        :nil nil?))

(s/def ::txid (s/coll-of string?))
(s/def ::orders-info-params
  (s/or :params (s/keys :req-un [::txid]
                        :opt-un [::trades
                                 ::userref])
        :nil nil?))

(s/def :trade/type #{"all" "any position" "closed position" "closing position" "no position"})
(s/def ::trades-history-params
  (s/or :params (s/keys :opt-un [:trade/type
                                 ::trades
                                 ::start
                                 ::end
                                 ::ofs])
        :nil nil?))

(s/def ::trades-info-params
  (s/keys :req-un [::txid]
          :opt-un [::trades]))

(s/def ::docalcs boolean?)
(s/def ::open-positions-params
  (s/keys :req-un [::txid]
          :opt-un [::docalcs]))

(s/def ::ledgers-info/type #{"all" "deposit" "withdrawal" "trade" "margin"})
(s/def ::ledgers-info-params
  (s/or :params (s/keys :opt-un [::aclass
                                 :multiple/asset
                                 ::start
                                 ::end
                                 ::ofs])
        :nil nil?))

(s/def :ledgers/id (s/coll-of string?))
(s/def ::ledgers-params
  (s/keys :req-un [:ledgers/id]))

(s/def ::fee-info boolean?)
(s/def ::trade-volume-params
  (s/or :params (s/keys :opt-un [:multiple/pair
                                 ::fee-info])
        :nil nil?))
