(ns tab.tab-02
  (:require [cljfx.api :as fx]
            [tab.view.tab :as tab])
  (:import [javafx.scene.input KeyCode KeyEvent]))

(declare renderer)

(defn init-state []
  {:selection ["/etc"
               "/users/vlaaad/.clojure"]
   :tree {"dev" {}
          "etc" {}
          "users" {"vlaaad" {".clojure" {"deps.edn" {}}}}
          "usr" {"bin" {}
                 "lib" {}}}})

(defonce *state
  (atom (init-state)))


(defn map-event-handler [e]
  (when (and (= :event/scene-key-press (:event/type e))
             (= KeyCode/ESCAPE (.getCode ^KeyEvent (:fx/event e))))
    (reset! *state nil)))

(def renderer
  (fx/create-renderer
   :middleware (fx/wrap-map-desc (fn [state]
                                   {:fx/type tab/view
                                    :state state}))
   :opts {:fx.opt/map-event-handler map-event-handler}))

(fx/mount-renderer *state renderer)

(renderer)









