(ns snake-server.core)

(def map-size [20, 40])

(def initial-state
  {:status :playing
   :snake (list [1, 1])
   :snake_dir :right
   :food_pos [10, 10]})

(defn snake-head [state] (first (:snake state)))

(defn points [state] (count (:snake state)))

(defn- draw-cell [[row_n col_n] state]
  (cond
    (= row_n 0) "="
    (= row_n (map-size 0)) "="
    (= col_n 0) "|"
    (= col_n (map-size 1)) "|"
    (= [row_n, col_n] (:food_pos state)) "o"
    (some #(= % [row_n col_n]) (:snake state)) "#"
    :else " "))

(defn draw [state]
  (loop [row 0, col 0]
    (print (draw-cell [row col] state))
    (when (> col (map-size 1)) (println))
    (cond
      (> row (map-size 0)) nil
      (<= col (map-size 1)) (recur row (+ 1 col))
      :else (recur (+ 1 row) 0))))

(defn- gen-new-snake-head [dir state]
  (let [[head-x, head-y] (snake-head state)]
    (case dir
      :right [head-x, (+ 1 head-y)]
      :left [head-x, (- 1 head-y)]
      :up [(- 1 head-x), head-y]
      :down [(+ 1 head-x), head-y])))

(defn- move-snake [input state]
  (let [dir (or (:dir input) (:snake_dir state))
        new-snake-head (gen-new-snake-head dir state)]
    (-> state
        (update :snake #(cons new-snake-head %)))))

(defn- check-collision
  [state]
  nil)

(defn- check-food-eaten
  [state]
  (if (= (:food_pos state) (snake-head state))
    ()
    state)

(defn next-state [input state]
  (cond
    (= :finished (:status state)) state
    :else (move-snake input state)
  ))


(next-state {} initial-state)

(next-state {} {:status :finished})
