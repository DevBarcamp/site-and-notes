(ns paren.app
  (:require 
    [reagent.core :as r :refer [atom]]
    [paren.cmds :as cmds]
    [paren.component :as com]
    [goog.dom :as gdom]
    [goog.events :as gevents]))

(defonce app-state 
  (atom cmds/init-state))

(defonce viewport
  (r/atom
    {:h (-> js/document .-documentElement .-clientHeight)
     :w (-> js/document .-documentElement .-clientWidth)}))

(gevents/listen 
  js/window 
  goog.events.EventType.RESIZE
  #(reset! viewport {:h (-> js/document .-documentElement .-clientHeight)
                     :w (-> js/document .-documentElement .-clientWidth)}))

(defn content-editable [focus?]
  (let [text (atom "")
        handle-change #(let [ch (.-charCode %)]
                         (when (= ch 13)
                           (swap! app-state 
                                  (fn [state]
                                    (-> state
                                        (update :history conj @text)
                                        (assoc  :bullshit (cmds/random-bullshit)))))
                           (reset! text "")))]
    (fn []
      [:div.content-editable
        {:style {:position "relative"
                 :width (if @focus? 
                          (str (min 350 (- (:w @viewport) 55)) "px") 
                          "120px")
                 :transition "all ease 0.2s"}}
        [:div
          {:style {:font-size "18px"
                   :line-height "45px"
                   :font-family "'Share Tech Mono', monospace"
                   :color "#ccc"
                   :position "absolute"}}
          (cmds/fuzzy-match @text)]
        [:input
          {:style {:border "none"
                   :padding "0"
                   :position "absolute"
                   :width "100%"
                   :font-size "18px"
                   :font-family "'Share Tech Mono', monospace"
                   :background-color "rgba(0,0,0,0)"
                   :line-height "45px"}
           :placeholder (if @focus? 
                          "Type '?' or 'detail'"
                          "Click here")
           :on-focus #(reset! focus? true)
           :on-blur #(reset! focus? false)
           :on-key-press handle-change
           :on-change #(reset! text (-> % .-target .-value))
           :value @text}]])))     

(defn paren-box [focus?]
  (fn []
    [:div.paren-box
      {:style {:margin-top "20px"}}
      [:div.container
        {:style 
          {:display "flex"
           :font-family "'Share Tech Mono', monospace"
           :font-size "40px"}}
        [:div
          {:style {:padding-right "5px"}}
          "("]
        [content-editable focus?]
        [:div 
          {:style {:padding-left "5px"}}
          ")"]]]))

(defn eval-note [data]
  (let [text (r/cursor data [:bullshit])
        terminal-bg (r/cursor data [:terminal-bg])
        terminal-color (r/cursor data [:terminal-color])
        history (r/cursor data [:history])]
    (fn []
      [:div.eval-note
        {:style {:max-width (str (min (- (:w @viewport) 20) 450) "px")
                 :width "100%"
                 :height "200px"
                 :background-color @terminal-bg
                 :overflow "hidden"
                 :font-size "12px"
                 :color @terminal-color
                 :padding "10px"}}
        (let [cmd (str (last @history))
              cmd-fn (get cmds/dict (cmds/fuzzy-match cmd))]
          (if cmd-fn
            (cmd-fn data cmd)
            [cmds/typewriter {:text text}]))])))

(defn front-card [app-state flipped?]
  (let [focus? (atom false)]
    (fn []
      [:div
        {:style {:display "flex" 
                 :justify-content "center"
                 :align-items "center"
                 :flex-direction "column"
                 :flex "1 0 auto"}}
        [:h1 {:style {:font-size "72px"
                      :line-height "1.5em"
                      :display (if (and @focus? (> 768 (:w @viewport))) "none" "initial")}} 
          (str (-> @app-state :title) (-> @app-state :events count))]
        [:h2 {:style {:margin-bottom "20px"
                      :display (if (and @focus? (> 768 (:w @viewport))) "none" "initial")}} 
          (-> @app-state :subtitle)]
        (if (-> @app-state :history first)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
          [eval-note app-state])
        [paren-box focus?]
        [:p "- OR -"]
        [:a {:href "#"
             :style {:font-family "Helvetica, Arial"}
             :on-click #(swap! flipped? not)}
          "Just show me everyting"]])))

(defn back-card [app-state flipped?]
  (fn []
    [:div
      {:style {:display "flex" 
               :justify-content "center"
               :align-items "center"
               :flex-direction "column"
               :flex "1 0 auto"}}
      [:h1 {:style {:font-size "48px"
                    :line-height "1.2em"
                    :display (if (-> @app-state :focus) "none" "initial")}} 
        (str (-> @app-state :title) (-> @app-state :events count))]
      [:div {:style {:max-width "450px"
                     :display "flex"
                     :flex-direction "column"
                     :justify-content "center"
                     :align-items "center"
                     :font-family "Roboto"}}
        [:h4 {:style {:margin-top "10px" :margin-bottom "5px"}} "報名"]
        [:div.back-links
           {:style {:display "flex"
                    :justify-content "space-around"
                    :align-items "center"
                    :width "120px"}}
           [:a.fb {:href "https://www.facebook.com/devbarcamp.hk/"
                   :style {:display "block"}}
             [:i.fa.fa-facebook-official.fa-2x]]
           [:a.mu {:href "https://www.meetup.com/Dev-Barcamp/"
                   :style {:display "block"}}
             [:i.fa.fa-meetup.fa-2x]]]
        [:h4 {:style {:margin-top "10px" :margin-bottom "5px"}} "日期"]
        [:div {:style{:text-align "center"}}
          (str (-> @app-state :events last :date) 
               " " 
               (-> @app-state :events last :time))]
        [:h4 {:style {:margin-top "10px" :margin-bottom "5px"}} "地點"]
        [:div {:style {:text-align "center"}} (-> @app-state :events last :location)]
        (when (-> @app-state :events last :gmap-img)
          [:a {:href (-> @app-state :events last :gmap)}
            [:img {:src (-> @app-state :events last :gmap-img)
                   :width "400px"
                   :height "300px"
                   :style {:width (str (min (- (:w @viewport) 10) 400) "px") :height "100%"} 
                   :alt (-> @app-state :events last :location)}]])]
      [:a {:href "#"
           :style {:font-family "Helvetica, Arial"
                   :margin-top "10px"}
           :on-click #(swap! flipped? not)}
        "Give me back! My Terminal!"]]))

(defn main []
  (let [flipped? (r/atom false)]
    [:div
      {:style {:width (str (:w @viewport) "px")
               :height (str (:h @viewport) "px")
               :min-height "534px"
               :display "flex"
               :flex-direction "column"}}
      [:div
        {:style {:flex "1 0 auto"}}
        [com/flipbox {:flipped? flipped?}
          [front-card app-state flipped?]
          [back-card app-state flipped?]]]
      [:div.footer
        {:style {:font-size "0.8rem"
                 :font-style "italic"
                 :color "#444"
                 :text-align "center"}}
        "Powered by Clojure. Release under EPL."]]))
      
(defn ^:export init []
  (r/render-component 
    [main]
    (.getElementById js/document "container")))
