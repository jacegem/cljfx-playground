(ns tut.tab
  (:require [cljfx.api :as fx]
            [cljfx.ext.tab-pane :as fx.ext.tab-pane])
  (:import [javafx.scene.control Tab]))

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


(defn- with-tab-selection-props [props desc]
  {:fx/type fx.ext.tab-pane/with-selection-props
   :props props
   :desc desc})

(defn- let-refs [refs desc]
  {:fx/type fx/ext-let-refs
   :refs refs
   :desc desc})

(defn- get-ref [ref]
  {:fx/type fx/ext-get-ref
   :ref ref})

(defn tab-pane [{:keys [items selection selection-capabilities]}]
  {:pre [(set? selection-capabilities)]}
  (let [selected-tab-id (-> selection sort first)
        _ (assert selected-tab-id)]
    (let-refs (into {}
                    (map (fn [item]
                           {:pre [(string? item)]}
                           [item
                            (merge
                             {:fx/type :tab
                              :graphic {:fx/type :label
                                        :text (str "!23" item)}
                              :id item
                              :closable false}
                             (cond-> {}
                                ; buggy for :read'ing tabs
                               (:write selection-capabilities)
                               (assoc :content {:fx/type :label
                                                :text item})

                               (not (:write selection-capabilities))
                               (assoc :disable (if selected-tab-id
                                                 (not= item selected-tab-id)
                                                 true))))]))
                    items)
              (with-tab-selection-props
                (cond-> {}
                  (:read selection-capabilities) (assoc :selected-item (get-ref selected-tab-id))
                  (:write selection-capabilities) (assoc :on-selected-item-changed {:event/type ::select-tab}))
                {:fx/type :tab-pane
                 :tabs (map #(-> (get-ref %)
                                 (assoc :fx/id %))
                            items)}))))

(defn- make-path->value [tree]
  (letfn [(flatten-map [prefix x]
            (mapcat (fn [[k v]]
                      (let [path (str prefix "/" k)]
                        (cons [path v] (flatten-map path v))))
                    x))]
    (into (sorted-map)
          (flatten-map "" tree))))

(defn view [{{:keys [tree selection]} :state}]
  (let [path->value (make-path->value tree)]
    {:fx/type :stage
     :showing true
     :width 960
     :scene {:fx/type :scene
             :root {:fx/type :scroll-pane
                    :fit-to-width true
                    :fit-to-height true
                    :content {:fx/type tab-pane
                              :items ["A" "B"] #_(keys path->value)
                              :selection-capabilities #{:write}
                              :selection selection}}}}))

(defn tab-id [^Tab x]
  {:post [(string? %)]}
  (.getId x))


(def renderer
  (fx/create-renderer
   :middleware (fx/wrap-map-desc (fn [state]
                                   {:fx/type view
                                    :state state}))
   :opts {:fx.opt/map-event-handler
          #(case (:event/type %)
             ::select-tab
             (swap! *state assoc :selection [(-> % :fx/event tab-id)]))}))



(fx/mount-renderer *state renderer)

(renderer)
