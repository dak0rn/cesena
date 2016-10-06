;; security.clj - security utilities
(ns cesena.services.security
  (:require [ buddy.sign.jwt :as jwt ]
            [ buddy.hashers :as hashers ]
            [ clj-time.core :as time ]
            [ cesena.config :refer [ config ] ]))

(defn
  ^{
    :doc "Hashes the given string using the configured algorithm"
    :added "0.1.0"
  }
  encrypt
  [ input ]
  (hashers/derive input {:alg (get-in config [:security :hashing-algorithm]) }))

(defn
  ^{
    :doc "Verifies the given plain text string with the given encrypted
          string. Returns true if the encrypted one can be derived from
          the plain text one"
    :added "0.1.0"
  }
  verify
  [ plain encrypted ]
  (hashers/check plain encrypted))

(defn
  ^{
    :doc "Creates a JWT with optional extra claims"
    :added "0.1.0"
  }
  create-jwt

  ;; w/out additonal claims
  ([] (create-jwt {}))

  ;; w/ additional claims
  ([ claims ]
   (let [ jwt-config (get-in config [:security :jwt])
          exp (time/plus (time/now) (time/seconds (:ttl jwt-config)))
          all-claims (merge {:exp exp} claims (:claims jwt-config)) ]
     (jwt/sign all-claims (:key jwt-config)))))

(defn
  ^{
     :doc "Validates the claim and returns the claims if valid"
     :added "0.1.0"
  }
  get-claims
  [ token ]
  (let [ jwt-config (get-in config [ :security :jwt ])
         enckey (:key jwt-config) ]
    (jwt/unsign token enckey )))

(defn
  ^{
     :doc "Adds the JWT cookie to the given ring response map"
     :added "0.1.0"
  }
  add-jwt-cookie
  [ response token ]
  (let [ field-name (get-in config [ :security :jwt :field ]) ]
    (assoc-in response [ :cookies field-name ] { :value token :http-only true } )))

(defn
  ^{
     :doc "Removes the JWT cookie from the response map"
     :added "0.1.0"
  }
  remove-jwt-cookie
  [ response ]
  (let [ field-name (get-in config [ :security :jwt :field ]) ]
    (assoc-in response [ :cookies field-name ] { :value "" :http-only true :max-age -1 })))
