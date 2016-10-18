;; upload.clj - Routes used when uploading a file
(ns cesena.routes.upload
  (:require [ cesena.services.upload :refer [ store-book ] ]
            [ compojure.core :refer [ GET POST ] ]
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


(def routes [
  (GET "/upload" request (handle-upload request))
])
