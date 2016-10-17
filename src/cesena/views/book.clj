;; book.clj - Views for book details
(ns cesena.views.book
  (:require [ cesena.views.partials.document :refer [ document ] ]
            [ cesena.views.partials.navigation :refer [ navigation ] ]
            [ clj-time.coerce :refer [ from-long ] ]
            [ hiccup.core :refer [ h ] ]))

(def detail-props [
  { :p :book_id :t "Book ID" }
  { :p :title :t "Title" }
  { :p :checksum :t "Checksum" }
  { :p :date :t "Edit/Create date" }
])

(defn
  render-book
  "Renders the given book"
  { :added "0.1.0" }
  [ book book-file render-inline user ]
  (document (h (:title book))
    (navigation user)
    [ :main
      [ :h1 (h (:title book)) ]
      [ :div.book-toolbar
        [ :a { :href (str "/serve/" (:book_id book)) :target "_blank" } "[ Download ]" ]
        (if render-inline
          [ :a { :href "?view=false" } "[ Hide inline view ]" ]
          [ :a { :href "?view=true" } "[ View inline ]" ]
        )
        [ :a { :href (str "/edit/" (:book_id book)) } "[ Edit ]" ]
      ]
      [ :div.mt-40 ]
      [ :div.book-grid
        [ :div.book-left
          [ :h2 "Book information" ]
          [ :div.book-details
            (for [ detail detail-props ]
               [ :div.detail-row
                 [ :div.detail-label (:t detail) ]
                 [ :div.detail-value (h (get book (:p detail))) ]
               ])
          ]
          [ :div.mt-40 ]
          [ :h2 "File information" ]
          [ :div.book-details
            [ :div.detail-row
              [ :div.detail-label "Full path" ]
              [ :div.detail-value (.getAbsolutePath book-file) ]
            ]
            [ :div.detail-row
              [ :div.detail-label "Permissions" ]
              [ :div.detail-value
                (when (.canRead book-file) "R")
                (when (.canWrite book-file) "W")
                (when (.canExecute book-file) "X")
              ]
            ]
            [ :div.detail-row
              [ :div.detail-label "Last modified" ]
              [ :div.detail-value (-> book-file .lastModified from-long str) ]
            ]
          ]
        ]
        (if render-inline
          [ :div.book-right
            [ :iframe { :src (str "/serve/" (:book_id book)) } ]
          ])
      ]
    ]))
