;; project.clj - taobaibai project configuration
(defproject taobaibai "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "https://github.com/dak0rn/taobaibai"
  :main taobaibai.core
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"
            :key "mit"
            :year 2016}
  :dependencies [
                 [org.clojure/clojure "1.8.0"]
                 [org.xerial/sqlite-jdbc "3.8.11.2"]
                 [com.layerware/hugsql "0.4.7"]
                 [mount "0.1.10"]
                 [buddy "1.0.0"]
                ])
