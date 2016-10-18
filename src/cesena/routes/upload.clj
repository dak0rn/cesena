;; upload.clj - Routes used when uploading a file
(ns cesena.routes.upload
  (:require [ cesena.services.upload :refer [ store-book ] ]
            [ clojure.string :refer [ blank? ] ]
            [ compojure.core :refer [ GET POST ] ]
            [ ring.util.response :refer [ redirect ] ]
            [ cesena.services.library :refer [ folder-locked? ] ]
            [ cesena.views.upload :refer [ render-upload ] ]))

(defn
  handle-upload
  "Handler for /upload/"
  { :added "0.1.0" }
  [ request ]
  (let [ locked (folder-locked?)
         user (:cesena-session request )]
    (render-upload locked user)))

(defn
  do-upload
  "Handles an uploaded file"
  { :added "0.1.0" }
  [ request ]
  (let [ params (:params request)
         title (:name params)
         filedata (:datfile params) ]
    (if (or (blank? title) (not filedata))
      (redirect "/upload?error=missing")
      (let [ tempfile (:tempfile filedata)
             name (:filename filedata)
             new-book (store-book title name tempfile) ]
        (redirect (str "/book/" (:book_id new-book)))))))

(def routes [
  (GET "/upload" request (handle-upload request))
  (POST "/upload" request (do-upload request))
])
