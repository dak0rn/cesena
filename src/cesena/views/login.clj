;; login.clj - View for the login
(ns cesena.views.login
  (:require [ cesena.views.partials.document :refer [ document ] ]
            [ ring.util.anti-forgery :refer [ anti-forgery-field ] ]
            [ hiccup.form :refer [ form-to ] ]))

(defn
  ^{
    :doc "Renders the login form"
    :added "0.1.0"
  }
  render-login
  ([ ] (render-login false))
  ([ has-error ]
    (document "Cesena Login"
            [ :div.login-window
              [ :div.login-body
                (when has-error [ :div.message.danger "Login failed" ])
                (form-to [ :post "/login" ]
                  (anti-forgery-field)
                  [ :div.form-row
                    [ :label { :for "username" } "Username" ]
                    [ :input#username { :type "text" :name "username" } ]
                  ]
                  [ :div.form-row
                    [ :label { :for "password" } "Password" ]
                    [ :input#password { :type "password" :name "password" } ]
                  ]
                  [ :div.submit-row
                    [ :button "Login" ]
                    [ :div.clearfix ]
                  ]
                )
              ]])))
