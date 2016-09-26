;; security.clj - security utilities
(ns cesena.services.security
  (:require [ buddy.sign.jwt :as jwt ]
            [ buddy.hashers :as hashers ]
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
