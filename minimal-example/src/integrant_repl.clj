(ns integrant-repl
  (:require [clojure.java.io :as io]
            [integrant.core :as ig]
            [integrant.repl :refer [set-prep! clear go halt prep init reset reset-all]]))

(def config
  {:adapter/jetty {:port 8080, :handler (ig/ref :handler/greet)}
   :handler/greet {:name "Alice"}})

(def config-2
  (ig/read-string (slurp (io/resource "config.edn"))))

(defmethod ig/init-key :adapter/jetty [_ {:keys [handler] :as opts}]
  (fn [_] "jetty"))

(defmethod ig/init-key :handler/greet [_ {:keys [name]}]
  (fn [what] (str "Hello " name what)))

(def system
  (ig/init config))

(-> config
    ig/prep
    ig/init)

;; (set-prep! config)





(comment
  (set-prep! config)
  (prep)
  (go)
  ;; (reset)
  ((:handler/greet system) "name")


  #_{})

