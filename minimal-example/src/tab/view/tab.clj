(ns tab.view.tab
  (:require [cljfx.ext.tab-pane :as fx.ext.tab-pane]
            [tab.view.setting :as setting]))



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
                            :props {:selected-index 2}
                            :desc {:fx/type :tab-pane
                                   :tabs [{:fx/type :tab
                                           :graphic {:fx/type :label
                                                     :text "first tab"}
                                           :content {:fx/type :label
                                                     :text "first content"}}

                                          setting/view
                                          setting/view

                                          {:fx/type :tab
                                           :graphic {:fx/type :label
                                                     :text "second tab"}
                                           :content {:fx/type :label
                                                     :text "second content"}}]}}}}})
