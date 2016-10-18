;; upload.clj - Upload view
(ns cesena.views.upload
  (:require [ cesena.views.partials.document :refer [ document ] ]
            [ cesena.views.partials.navigation :refer [ navigation ] ]
            [ ring.util.anti-forgery :refer [ anti-forgery-field ] ]
            [ hiccup.core :refer [ h ] ]
            [ hiccup.form :refer [ form-to ] ]))

(defn
  render-upload
  "Renders the upload screen"
  { :added "0.1.0" }
  [ locked user ]
  (document "Upload book"
    (navigation user)
    [ :h1 "Upload a book" ]
    (if locked
      [ :div.message.danger "The upload folder is not writable" ]
      (form-to { :enctype "multipart/form-data" } [ :post "/upload" ]
        (anti-forgery-field)
        [ :div.book-details
          [ :div.detail-row
            [ :div.detail-label "Title" ]
            [ :div.detail-value [ :input { :type "text" :name "name" :required true } ] ]
          ]
          [ :div.detail-row
            [ :div.detail-label ]
            [ :div.detail-value [ :input { :type "file" :name "datfile" :accept ".pdf" :required true } ] ]
          ]
          [ :div.detail-row
            [ :div.detail-label [ :a { :href "/" } "[ Cancel ]" ] ]
            [ :div.detail-value [ :button "Upload" ] ]
          ]
        ]
        ))))
