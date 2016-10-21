;; edit.clj - Edit routes for a book
(ns cesena.routes.edit
  (:require [ compojure.core :refer [ GET POST ] ]
            [ cesena.views.edit :refer [ render-edit ] ]
            [ cesena.routes :refer [ url ] ]
            [ ring.util.response :refer [ redirect ] ]
            [ cesena.services.library :refer [ get-book update-book ] ]))

(defn
  handle-edit
  "Handler for /edit/:id"
  { :added "0.1.0" }
  [ request ]
  (let [ book-id (get-in request [ :params :id ])
         user (:cesena-session request)
         message (get-in request [ :params :success ])
         book (get-book book-id) ]
    (if-not book
      (redirect (url "/?error=book-not-found"))
      (render-edit book user message))))

(defn
  do-edit
  "Handler for /edit/:id that updates a book"
  { :added "0.1.0" }
  [ request ]
  (if-let [ book (-> request :route-params :id get-book) ]
    (let [ new-book (assoc book :title (-> request :params :title)) ]
      (update-book new-book)
      (redirect (url (str "/edit/" (:book_id new-book) "?success=save" ))))
    (redirect (url "/?error=book-not-found"))))

(def routes [
  (GET "/edit/:id" request (handle-edit request))
  (POST "/edit/:id" request (do-edit request))
])
