(ns try-cljs
  (:require ["framer-motion" :refer [motion]]
            ["react-dom/client" :as react-client]
            [applied-science.js-interop :as j]
            [reagent.core :as r]))

(defn framer-motion-demo []
  (r/with-let [!state (r/atom {:x -5
                               :y 0
                               :scale 1
                               :rotate 0})]
    (js/console.log :state @!state)
    [:div.mx-auto {:class "w-[500px]"}
     [:div.shadow-inner.rounded-lg.border.mt-8.flex.items-center.justify-center
      {:style {:width 500 :height 500}}
      [:> motion.div (merge
                      {:class "rounded-md shadow-xl"
                       :style {:background "#de00ea"
                               :width 200
                               :height 200}}
                      {:animate @!state})]]
     [:div.mt-4.flex.flex-col.gap-3
      [:div.flex.items-center.gap-3
       [:label "x"]
       [:input.border.text-right.rounded.p-1.shadow-inner
        {:type "number"
         :class "max-w-[100px]"
         :value (:x @!state)
         :on-input (fn [event]
                     (swap! !state assoc :x (js/parseInt (j/get-in event [:target :value]) 10)))}]
       [:input
        {:type "range"
         :value (:x @!state)
         :min -100
         :max 100
         :on-input (fn [event]
                     (swap! !state assoc :x (js/parseInt (j/get-in event [:target :value]) 10)))}]]
      [:div.text-slate-400.font-bold
       "You do the rest ;)"]]]))

(defn app []
  [framer-motion-demo])

(defonce ^js react-root
  (when-let [root-el (and (exists? js/document)
                          (js/document.getElementById "app"))]
    (react-client/createRoot root-el)))

(defn ^:dev/after-load main []
  (when react-root
    (.render react-root (r/as-element [app]))))
