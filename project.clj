(defproject blog-scrape "0.1.0-SNAPSHOT"
  :description "reformat blog in the correct order"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
                 [enlive "1.1.5"] 
                 [org.clojure/clojure "1.6.0"]]
  :main ^:skip-aot blog-scrape.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
