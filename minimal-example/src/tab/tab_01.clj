(ns tab.tab-01
  (:require [cljfx.api :as fx])
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

(defn view [state]
  {:fx/type :stage
   :always-on-top true
   :showing true
   :x -1000
   :width 500
   :height 500
   :scene {:fx/type :scene
           :root {:fx/type :scroll-pane
                  :fit-to-height true
                  :fit-to-width true
                  :content {:fx/type :tab-pane
                            :tabs [{:fx/type :tab
                                    :graphic {:fx/type :label
                                              :text "first tab"}
                                    :content {:fx/type :label
                                              :text "first content"}}
                                   {:fx/type :tab
                                    :graphic {:fx/type :label
                                              :text "second tab"}
                                    :content {:fx/type :label
                                              :text "second content"}}]}}}})

(renderer)

(defn map-event-handler [e]
  (when (and (= :event/scene-key-press (:event/type e))
             (= KeyCode/ESCAPE (.getCode ^KeyEvent (:fx/event e))))
    (reset! *state nil)))


(def renderer
  (fx/create-renderer
   :middleware (fx/wrap-map-desc (fn [state]
                                   {:fx/type view
                                    :state state}))
   :opts {:fx.opt/map-event-handler map-event-handler}))

(fx/mount-renderer *state renderer)


(renderer)


