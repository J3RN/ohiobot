(defproject ohiobot "0.1.0-SNAPSHOT"
  :description "A very simple bot that replies IO! to every variation of 'OH'"
  :url "https://github.com/j3rn/ohiobot"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure   "1.5.1"]
                 [irclj                 "0.5.0-alpha4"]
                 [org.clojure/java.jdbc "0.3.5"]
                 [postgresql/postgresql "8.4-702.jdbc4"]
                 [korma                 "0.4.0"]]
  :repl-options {:init-ns ohiobot.core}
  :main "ohiobot.core/start")
