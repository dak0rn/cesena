;; admin.clj - Admin views
(ns cesena.views.admin
  (:require [ cesena.views.partials.document :refer [ document ] ]
            [ ring.util.anti-forgery :refer [ anti-forgery-field ] ]
            [ hiccup.form :refer [ form-to ] ]
            [ cesena.views.partials.navigation :refer [ navigation ] ]
            ))

;; Helper that creates a form performing a POST
;; to the given url with the given user
(defn- button-form
  [ url label { uid :user_id } ]
  (form-to [ :post url ]
    (anti-forgery-field)
    [ :input { :type "hidden" :name "uid" :value uid } ]
    [ :button label ]))

(def lock-user-form (partial button-form "/admin/lock" "Lock"))
(def delete-user-form (partial button-form "/admin/delete" "Delete"))

;; Form used to change a user's password
(defn- change-password-form
  [ user checkbox-id ]
  (form-to [ :post "/admin/change" ]
    (anti-forgery-field)
    [ :input { :type "hidden" :name "uid" :value (:user_id user) } ]
    [ :input { :type "password" :name "password" } ]
    [ :label.pw-label-trigger { :for checkbox-id } "Cancel" ]
    [ :button "Submit" ]))

;; User list
(defn- user-list
  [ all-users ]
  [ :div.user-list
   [ :h2 "Users" ]
   (for [ user all-users ]
     (let [ is-locked (nil? (:passwd user))
            checkbox-id (str "showpw" (:user_id user)) ]
       [ :div.user-row
         [ :input { :type "checkbox" :id checkbox-id } ]
         [ :div.user-name (:name user) ]
         [ :div.user-locked (when is-locked "Locked") ]
         [ :div.user-admin (when (= 1 (:admin user)) "Admin") ]
         [ :div.user-button
          (if (not is-locked) (lock-user-form user) )
          (delete-user-form user)
          [ :label.pw-label-trigger { :for checkbox-id } "Change Password" ] ]
         [ :div.user-pw-form (change-password-form user checkbox-id) ]
     ]))])

;; Form used to create a new user
(defn- user-create-form
  [ ]
  (form-to [ :post "/admin/create" ]
    [ :h2 "Create user" ]
    (anti-forgery-field)
    [ :label "Username" ]
    [ :input { :name "name" :placeholder "Username" :required true :type "text" } ]
    [ :label "Password" ]
    [ :input { :name "passwd" :placeholder "Password" :type "password" :required true } ]
    [ :label
      "Is Admin?"
      [ :input { :type "checkbox" :name "admin" } ]
     ]
    [ :div [ :button "Submit" ] ]
))


;; Renders a list of error messages
(defn- render-error
  [ error-type ]
  (when (not (empty? error-type))
    [ :div.message.error
     (case error-type
       "values-missing" "Could not create the user because the input was invalid"
       ;; Default
       "An unknown error occured")
     [ :a { :href "/admin" :class "dismiss" } "&times;" ]]))

(defn- render-success
  [ success-type ]
  (when (not (empty? success-type))
    [ :div.message.success
     (case success-type
       "created" "The user has been created"
       "delete" "The user has been deleted"
       "change" "The password has been changed"
       "locked" "The user has been locked. Change their password to unlock them"
       ;; Default
       "Yeah... something")
     [ :a { :href "/admin" :class "dismiss" } "&times;" ]]))

;; Renders the form that allows to recreate the database
(defn- rescan-form
  [ ]
  (form-to [ :post "/admin/rescan" ]
    [ :h2 "Rescan library" ]
    [ :div "This may take a moment" ]
    (anti-forgery-field)
    [ :button "Recreate library index" ]))

(defn
  ^{
     :doc "Renders the admin overview page"
     :added "0.1.0"
  }
  render-admin
  [ session all-users message-options rescanning-error ]
  (document "Cesena Admin"
    (navigation session)
    [ :main
      [ :h1 "Cesena Administration" ]
      (render-error (get message-options "error"))
      (render-success (get message-options "success"))
      [ :div.admin-grid
        [ :div.admin-left (user-list all-users) ]
        [ :div.admin-right (user-create-form) (rescan-form) ]
      ]
      (when rescanning-error
        [ :div.message.error
            "An error occured during rescanning"
            [ :pre rescanning-error ]
        ])
    ]))
