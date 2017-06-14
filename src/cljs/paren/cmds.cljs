(ns paren.cmds
  (:require 
    [reagent.core :as r]
    [clojure.string :as string]
    [cljs.reader :as rdr]))

(enable-console-print!)

(declare dict)

(defn help-text [_]
  [:pre
    (string/join "\n"
      (filter 
        identity
        (map 
          (fn [[k v]]
            (if (get (meta v) :desc)
              (str k "\t" (get (meta v) :desc))
              nil))
          dict)))])

(defn timer [delay stop? cb]
  (if (not (stop?))
    (cb))
  (js/setTimeout #(timer delay stop? cb) delay))

(defn typewriter [{:keys [delay text]
                   :or {delay 50}}]
  (let [position (r/atom 0)
        cur-text (r/atom @text)]
    (r/create-class
      {:component-did-mount
       (fn []
        (js/setTimeout #(timer delay 
                               (fn []
                                (<= (count @cur-text) @position)) 
                               (fn [] 
                                (swap! position inc)))))
       :component-will-unmount
       (fn [] (reset! position (count @cur-text)))
       :reagent-render
       (fn []
         (when (not= @cur-text @text)
          (reset! cur-text @text)
          (reset! position 0))
         [:pre (subs @cur-text 0 @position) (if (< (count @cur-text) @position) "\u2588" "")])})))

(defn random-bullshit []
  (let [bullshits ["/(# - ___-)/ Please don't break this website"
                   "Don't you think javascript is cool, Huh?"
                   "'hello world' is funnier than random typing."
                   "Here is the deal. Join us or die!"
                   "竟然俾你迫到我要講中文。。。"]]
    (rand-nth bullshits)))

(def init-state
  {:history []
   :events [{:date "2016-10-08" :time "1:00-3:00" :location "Block A, 2/F, Hong Kong Industrial Building, 444-452 Des Voeux Road West, Hong Kong  "}
            {:date "2017-07-08" :time "1:00-3:00" :location "Unit 05-07, 7/F, Block B, Chung Mei Centre, 15 Hing Yip Street, Kwun Tong, Kowloon, Hong Kong" :gmap "https://maps.google.com/maps?f=q&hl=en&q=15+Hing+Yip+Street%2C+Kwun+Tong%2C%2C+Kowloon%2C+hk" :gmap-img "https://maps.googleapis.com/maps/api/staticmap?key=AIzaSyDw_EVAIB36VOVoO-oJEX4RoQhG-JHQ8xY&center=Chung+Mei+Centre,+15+Hing+Yip+Street,+Kwun+Tong,+Kowloon,+Hong+Kong&zoom=16&scale=1&size=400x300&maptype=roadmap&format=png&visual_refresh=true&markers=size:mid%7Ccolor:0xff0000%7Clabel:1%7CChung+Mei+Centre,+15+Hing+Yip+Street,+Kwun+Tong,+Kowloon,+Hong+Kong"}]
   :social {"Facebook" "https://www.facebook.com/devbarcamp.hk/"
            "Meetup" "https://www.meetup.com/Dev-Barcamp/"}
   :title "Dev Barcamp #"
   :subtitle "香港人香港話既 Barcamp"
   :terminal-color "#32cd32"
   :terminal-bg "black"
   :long-desc "Dev Barcamp是個分享知識的聚會，任何人有點子想獻給大家
，或想找東西學習，那就走對地方了！
這裏會屬於大家的空間，彼此分享和學習。
分享內容由參與者帶動，進行各式各樣的討論、示範，讓大家打成一片。

分享以廣東話為主；

每個題目為時20分鐘；"
   :bullshit (random-bullshit)
   :banner 
"___  ____ _  _                           
|  \\ |___ |  |                           
|__/ |___  \\/                            
                                         
      ___  ____ ____ ____ ____ _  _ ___  
      |__] |__| |__/ |    |__| |\\/| |__] 
      |__] |  | |  \\ |___ |  | |  | |    
                                         
"})

(def jokes
  {"rm -rf /"
   (fn [_] [typewriter {:text (r/atom "Start removing your data....................... ....................................kidding")}])
   "+ 1 1"
   (fn [_] "Doing some math is good for your health")
   "hello world!"
   (fn [_] [typewriter {:text (r/atom "Congratulations! you are now a certified Programmer")}])
   "show me the money" 
   (fn [_] "I know you really a Starcraft lover.")
   "exit" ^{:desc "quit this repl"}
   (fn [_] [:pre "Are you thinking too much?"])
   "reboot" ^{:desc "Don't do that!"}
   (fn [_]
    [:div
      [:i.fa.fa-linux.fa-5x]
      [typewriter {:delay 10 :text (r/atom "Configurating ISA PNP
Setting system time from the hardware clock (localtime)
Using /etc/random-seed to initialize /dev/urandom\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008
Initializing basic system settings ...\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008
Updating shared libraries\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008
System ready ...
Type '?' to continue")}]])})

(def dict
  (merge
    {"barcamp" ^{:desc "describe what's Dev Barcamp"}
     (fn [data]
      [typewriter
        {:delay 20 :text (r/atom (-> @data :long-desc))}])
     "detail" ^{:desc "display next Dev Barcamp's details"}
     (fn [data]
      (let [{:keys [date time location gmap]} (-> @data :events last)] 
        [:div
          [:div "Date: " date]
          [:div "Time: " time]
          [:div "Location: " location " "
            [:a {:href gmap
                 :text-decoration "underline"} "(map)"]]]))
     "date" ^{:desc "display next Dev Barcamp's date"}
     (fn [data] [typewriter {:text (r/atom (-> @data :events last :date))}])
     "previous"
     (fn [_] "N/A")
     "attend" ^{:desc "claim you want to join the next Dev Barcamp!"}
     (fn [_]
       [:div {:style {:display "flex"
                      :flex-direction "column"
                      :justify-content "center"
                      :align-items "center"}}
        [typewriter {:text (r/atom "Click and attend Dev Barcamp!")}]
        [:br]
        [:br]
        [:div
         {:style {:display "flex"
                  :justify-content "space-around"
                  :align-items "center"
                  :width "200px"}}
         [:a {:href "https://www.facebook.com/events/1946131938957255/"
              :style {:display "block"
                      :width "60px"}}
           [:i.fa.fa-facebook-official.fa-5x]
           [:div "Facebook"]]
         [:a {:href "https://www.meetup.com/Dev-Barcamp/events/235015864/"
              :style {:display "block"
                      :width "60px"}}
           [:i.fa.fa-meetup.fa-5x]
           [:div {:style {:margin-left "11px"}} "Meetup"]]]])
     "like" ^{:desc "like Dev Barcamp and want to keep an eye on it!"}
     (fn [_]
       [:div {:style {:display "flex"
                      :flex-direction "column"
                      :justify-content "center"
                      :align-items "center"}}
        [:p "Click and like Dev Barcamp!"]
        [:div
         {:style {:display "flex"
                  :justify-content "space-around"
                  :align-items "center"
                  :width "200px"}}
         [:a {:href "https://www.facebook.com/devbarcamp.hk/"
              :style {:display "block"}}
           [:i.fa.fa-facebook-official.fa-5x]
           [:div "Facebook"]]
         [:a {:href "https://www.meetup.com/Dev-Barcamp/"
              :style {:display "block"}}
           [:i.fa.fa-meetup.fa-5x]
           [:div {:style {:margin-left "10px"}} "Meetup"]]]])
      
     "set!" ^{:desc "amend config. might cause reboot!"}
     (fn [d cmd]
      (let [[nam kw value] (rdr/read-string (str "[" cmd "]"))]
        (swap! d assoc (keyword kw) value)
        (str "set " kw " to " value)))
     "list" ^{:desc "list all avalible configuration key"}
     (fn [d]
       [:pre
         (str "available keys:\n\n"
           (string/join "\n" (map name (keys @d))))])
     "history" ^{:desc "show past events"}
     #(do (swap! % assoc :history ["history"]) "Not implemented yet.")
     "?" ^{:desc "print this text"}          
     help-text
     "report" ^{:desc "report a bug, or help us to improve"}
     (fn [_]
       [:div {:style {:display "flex"
                      :flex-direction "column"
                      :justify-content "center"
                      :align-items "center"}}
        [:p "Click and start contribute!"]
        [:div
         [:a {:href "https://www.github.com/DevBarcamp"}
           [:i.fa.fa-github-alt.fa-5x]
           [:div {:style {:padding-left "9px"}} "Github"]]]])
     "banner" ^{:desc "show banner"}
     (fn [d]
      [:pre (:banner @d)])
     "reset!" ^{:desc "reset all configuration"}
     (fn [d]
      (reset! d init-state)
      [:pre "Reset!"])}
    jokes))

(defn fuzzy-match [in]
  (if (not= in "")
    (first 
      (filter #(or
                 (string/starts-with? 
                   (string/upper-case %) 
                   (string/upper-case in))
                 (string/starts-with? 
                   (string/upper-case in) 
                   (string/upper-case %))) 
        (keys dict)))))