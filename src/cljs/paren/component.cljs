(ns paren.component)

(defn flipbox [{:keys [flipped?] :as props} front back]
  (fn []
    [:section.flipbox
      {:style (merge {:position "relative"
                      :perspective "800px"}
                     (dissoc props :flipped?))}
      [:div.card
        {:style (merge 
                  {:width "100%"
                   :height "100%"
                   :position "absolute"
                   :transform-style "preserve-3d"
                   :-webkit-transform-style "preserve-3d"
                   :transition "transform 1s"
                   :-webkit-transition "transform 1s"}
                  (if @flipped?
                    {:transform "rotateY(180deg)"
                     :-webkit-transform "rotateY(180deg)"}
                    {}))}
                        
        [:figure.front
          {:style {:margin 0
                   :position "absolute"
                   :width "100%"
                   :height "100%"
                   :backface-visibility "hidden"
                   :-webkit-backface-visibility "hidden"}}
          front]
        [:figure.back
          {:style {:margin 0
                   :position "absolute"
                   :width "100%"
                   :height "100%"
                   :transform "rotateY(180deg)"
                   :-webkit-transform "rotateY(180deg)"
                   :backface-visibility "hidden"
                   :-webkit-backface-visibility "hidden"}};
          back]]]))