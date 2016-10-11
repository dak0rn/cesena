;; routes.clj - collects routes and configures middlewares
(ns cesena.routes
  (:require [ compojure.core :refer :all ]
            [ ring.middleware.defaults :refer [ wrap-defaults site-defaults ] ]
            [ cesena.middlewares.session :refer [ wrap-session ] ]
            [ cesena.middlewares.locked :refer [ wrap-locked ] ]
            ;; Routes
            [ cesena.routes.index ]
            [ cesena.routes.login ]
            [ cesena.routes.logout ]
            [ cesena.routes.locked ]
            [ cesena.routes.admin ]
            ))

;; The router handler composed of all the routes
(def route-handler
  (apply routes
    (concat cesena.routes.index/routes
            cesena.routes.logout/routes
            cesena.routes.locked/routes
            cesena.routes.admin/routes
            cesena.routes.login/routes)))

;; Modified ring defaults
(def middleware-defaults
  ( -> site-defaults
    (assoc-in [ :static :resources ] "www" )))

;; The actual routes consisting of the
;; route definitions and middlewares
(def app-routes
  (-> route-handler
      ;; Session middleware
      wrap-session
      ;; Check the locked state of the application
      wrap-locked
      ;; Apply recommended middlewares for websites
      (wrap-defaults middleware-defaults)))
