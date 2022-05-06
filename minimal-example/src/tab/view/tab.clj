(ns tab.view.tab
  (:require [cljfx.ext.tab-pane :as fx.ext.tab-pane]
            [tab.view.first :refer [first-tab]]
            [tab.view.setting :as setting]))



(defn tab-changed [tab]
    (def tab-2 tab)
  (prn "TAB: " (class tab))
  (prn "FAB: " first-tab)
  (if (= tab first-tab)
    (prn "first tab")
    (prn "NOT first tab")))

(defn view [_state]
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
                  :content {:fx/type fx.ext.tab-pane/with-selection-props
                            :props {:selected-index 2
                                    :on-selected-item-changed tab-changed}
                            :desc {:fx/type :tab-pane
                                   :tabs [first-tab
                                          setting/view
                                          setting/view

                                          {:fx/type :tab
                                           :graphic {:fx/type :label
                                                     :text "second tab"}
                                           :content {:fx/type :label
                                                     :text "second content"}}]}}}}})
