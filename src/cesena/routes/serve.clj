;; serve.clj - Serves a book
(ns cesena.routes.serve
  (:require [ compojure.core :refer [ GET ] ]
            [ cesena.services.library :refer [ get-book ] ]
            [ clojure.java.io :refer [ file ] ]
            [ ring.util.response :refer [ header redirect file-response ] ]))

(defn-
  handle-serve
  "Handles /serve/:id and serves the file with the given id"
  { :added "0.1.0" }
  [ file-id ]
  (if-let [ book (get-book file-id) ]
    (-> (file-response (:path book) [ :index-files? false ])
        (header "Content-Type" "application/pdf")
        (header "Content-Disposition" (str "inline; filename='" (-> (:path book) file .getName) "'"))
        )
    (redirect "/?error=book-not-found")))

(def routes [
  (GET "/serve/:id" [ id ] (handle-serve id))
])
