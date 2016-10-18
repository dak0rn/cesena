;; edit.clj - Views for the book editing
(ns cesena.views.edit
  (:require [ hiccup.core :refer [ h ] ]
            [ hiccup.form :refer [ form-to ] ]
            [ ring.util.anti-forgery :refer [ anti-forgery-field ] ]
            [ cesena.views.partials.document :refer [ document ] ]
            [ cesena.views.partials.navigation :refer [ navigation ] ]))

(def detail-props [
  { :p :book_id :t "Book ID" }
  { :p :checksum :t "Checksum" }
  { :p :date :t "Edit/Create date" }
])

(defn
  render-edit
  "Renders the edit form for the given book"
  { :added "0.1.0" }
  ([ book user ] (render-edit book user nil))
  ([ book user message ]
  (document "Edit Book"
    (navigation user)
    [ :h1 "Edit Book" ]
    (form-to [ :post (str "/edit/" (:book_id book) ) ]
      [ :div.book-details.book-edit
        [ :div.detail-row
          [ :div.detail-label "Title" ]
          [ :div.detail-value
            (anti-forgery-field)
            [ :input { :type "hidden" :name "bid" :value (:book_id book) } ]
            [ :input { :type "text" :value (h (:title book)) } ]
          ]
        ]
        (for [ detail detail-props ]
          [ :div.detail-row
            [ :div.detail-label (:t detail) ]
            [ :div.detail-value (h (get book (:p detail))) ]
          ])
        [ :div.detail-row
          [ :div.detail-label [ :a { :href (str "/book/" (:book_id book)) } "[ Cancel ]" ] ]
          [ :div.detail-value [ :button "Save" ] ]
        ]
      ]
     ))))
