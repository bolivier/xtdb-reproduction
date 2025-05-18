(ns core
  (:require
   [xtdb.client :as xtc]
   [xtdb.api :as xt]))

(def node (xtc/start-client "http://localhost:3000"))

(def seed [_]
  (let [brandon-id #uuid"e15fdcfd-9e80-43e3-ba3b-440121f86187"
        olivia-id  #uuid"f2eb5db7-a4b8-4cf2-8729-264134b2d65a"]

    (xt/submit-tx node
                  [[:put-docs
                    :users
                    {:xt/id         brandon-id
                     :user/name     "Brandon"
                     :user/email    "brandon@users.com"
                     :user/password "password1234"}]
                   [:put-docs
                    :users
                    {:xt/id         olivia-id
                     :user/name     "Olivia"
                     :user/email    "olivia@users.com"
                     :user/password "password1234"}]])
    )
  )

(defn query []
  (xt/q node '(from :users [user/name user/email
                            {:user/email $email}])
        {:args {:email "user@email.com"}}))
