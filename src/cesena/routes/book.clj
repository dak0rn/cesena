;; book.clj - Detail view for a book

(ns cesena.routes.book
  (:require [ compojure.core :refer [ GET ] ]
            [ ring.util.response :refer [ redirect ] ]
            [ cesena.views.book :refer [ render-book ] ]
            [ clojure.java.io :refer [ file ] ]
            [ cesena.services.library :refer [ get-book ] ]))

(defn
  handle-book
  "Handler for /book"
  { :added "0.1.0" }
  [ request ]
  (let [ book-id (->> request :route-params :id)
         user (:cesena-session request) ]
    (if-let [ book (get-book book-id) ]
      (render-book book
                   (file (:path book))
                   (= "true" (get-in request [ :params :view ]))
                   user)
      (redirect "/?error=book-not-found"))))

(def routes [
  (GET "/book/:id" request (handle-book request))
])
