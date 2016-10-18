;; edit.clj - Edit routes for a book
(ns cesena.routes.edit
  (:require [ compojure.core :refer [ GET POST ] ]
            [ cesena.views.edit :refer [ render-edit ] ]
            [ ring.util.response :refer [ redirect ] ]
            [ cesena.services.library :refer [ get-book update-book ] ]))

(defn
  handle-edit
  "Handler for /edit/:id"
  { :added "0.1.0" }
  [ request ]
  (let [ book-id (get-in request [ :params :id ])
         user (:cesena-session request)
         book (get-book book-id) ]
    (if-not book
      (redirect "/?error=book-not-found")
      (render-edit book user))))


(def routes [
  (GET "/edit/:id" request (handle-edit request))
])
