;; notfound.clj - 404 handling
(ns cesena.routes.notfound
  (:require [ cesena.views.notfound :refer [ render-404 ] ]
            [ compojure.route :refer [ not-found ] ]))

(def routes [
  (fn [ request ] {
      :body (render-404)
      :headers {
        "Content-type" "text/html"
      }
   })
])
