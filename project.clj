;; project.clj - cesena project configuration
(defproject cesena "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "https://github.com/dak0rn/cesena"
  :main cesena.core
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"
            :key "mit"
            :year 2016}
  :dependencies [
    [org.clojure/clojure "1.8.0"]

    ;; SQLite connection library
    [org.xerial/sqlite-jdbc "3.8.11.2"]

    ;; SQL library
    [com.layerware/hugsql "0.4.7"]

    ;; State management system for code dependencies
    [mount "0.1.10"]

    ;; Library for hashing, encrypting and stuff
    [buddy "1.0.0"]

    ;; Route handling library
    [ ring "1.5.0" ]

    ;; Routing library that offers more flexibility
    ;; based on ring
    [ compojure "1.5.1" ]

    ;; Default middlewares for ring
    [ ring/ring-defaults "0.2.1" ]

    ;; Rendering library clojure -> html
    [ hiccup "1.0.5" ]

    ;; clojure's "moment" so to speak
    ;; [ clj-time "0.12.0" ]
    ]

  :plugins [
    ;; The ring plugin that allows to automate tasks
    [ lein-ring "0.9.7" ]
  ]

  ;; Options for the ring plugin
  :ring {
    ;; Route handler
    :handler cesena.routes/app-routes

    ;; Function to be invoked when starting the server/servlet
    :init cesena.core/boot-application

    ;; REPL connection
    :nrepl {
      :start? true
      :port 9987
    }
  }
)
