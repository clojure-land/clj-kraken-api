(def project 'clj-kraken-api)
(def version "0.0.1-SNAPSHOT")

(set-env! 
  :source-paths #{"src" "test"}
  :resource-paths #{"resources"} 
  :dependencies '[[org.clojure/clojure "1.9.0"]
                  [org.clojure/data.codec "0.1.1"]
                  [metosin/spec-tools "0.5.1"]
                  [aleph "0.4.4"]
                  [pandect "0.6.1"]
                  [cheshire "5.8.0"]])

(task-options! 
  aot {:namespace #{'autoscanner-etl.core}}
  pom {:project project,
       :version version,
       :description "Kraken API Library",
       :url "https://github.com/atsman/clj-kraken-api",
       :scm {:url "https://github.com/atsman/clj-kraken-api"},
       :license {"MIT"
                 "https://opensource.org/licenses/MIT"}}
  jar {:file (str "clj-kraken-api-" version "-standalone.jar")})
