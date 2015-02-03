(ns ohiobot.core-test
  (:require [clojure.test :refer :all]
            [ohiobot.core :refer :all]))

(deftest oh-detection-test
  (testing "oh true detection"
    (is (true? (is-oh "oh")))
    (is (true? (is-oh "OH")))
    (is (true? (is-oh "oh!")))
    (is (true? (is-oh "OH!!!!"))))
  (testing "oh false detection"
    (is (false? (is-oh "no")))
    (is (false? (is-oh "oh no!")))
    (is (false? (is-oh "Shiloh")))
    (is (false? (is-oh "Wait, oh!")))))

