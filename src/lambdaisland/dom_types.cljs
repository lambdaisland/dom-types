(ns lambdaisland.dom-types
  "Extend Pixi and DOM types with ClojureScript protocols."
  (:require [applied-science.js-interop :as j]
            [camel-snake-kebab.core :as csk]
            [clojure.core.protocols :as protocols]
            [clojure.string :as str]
            [lambdaisland.data-printers :as data-printers]))

(defn register-printer [type tag to-edn]
  (data-printers/register-print type tag to-edn)
  (data-printers/register-pprint type tag to-edn))

(defn register-keys-printer [type tag keys]
  (register-printer type tag (fn [obj]
                               (reduce (fn [m k]
                                         (assoc m k (j/get obj k)))
                                       {}
                                       keys)))
  (extend-type type
    protocols/Datafiable
    (datafy [obj]
      (into ^{:type type} {} (map (juxt identity #(j/get obj %))) keys))
    protocols/Navigable
    (nav [obj k v]
      (j/get obj k))))

(def ^:dynamic *max-attr-length* 250)

(defn- truncate-attr [attr-val]
  (if (and (string? attr-val) (< *max-attr-length* (.-length attr-val)))
    (str (subs attr-val 0 *max-attr-length*) "...")
    attr-val))

(defn hiccupize [^js e]
  (cond
    (string? e) e
    (instance? js/Text e) (.-data e)
    :else
    (when-let [tag-name (.-tagName e)]
      (let [el [(keyword (str/lower-case tag-name))]]
        (into (if-let [attrs (seq (.getAttributeNames e))]
                (conj el (into {}
                               (map (juxt csk/->kebab-case-keyword
                                          #(truncate-attr (.getAttribute e %))))
                               attrs))
                el)
              (keep hiccupize)
              (.-childNodes e))))))

(register-printer js/Text 'js/Text #(.-data %))
(register-printer js/Element 'js/Element hiccupize)
(register-printer js/DocumentFragment 'js/DocumentFragment #(map hiccupize (.-children %)))
(register-printer js/HTMLDocument 'js/HTMLDocument (fn [^js d] {:root (.-documentElement d)}))
(register-printer js/XMLDocument 'js/XMLDocument (fn [^js d] {:root (.-documentElement d)}))
(register-printer js/Document 'js/Document (fn [^js d] {:root (.-documentElement d)}))
(register-printer js/DOMStringMap 'js/DOMStringMap (fn [^js dataset]
                                                     (reduce (fn [m k]
                                                               (assoc m k (j/get dataset k)))
                                                             {}
                                                             (js/Object.keys dataset))))

(register-keys-printer js/Window 'js/Window [:location :document :devicePixelRatio :innerWidth :innerHeight])

(register-printer js/Location 'js/Location str)

(register-printer js/HTMLCollection 'js/HTMLCollection #(into [] %))
(register-printer js/NodeList 'js/NodeList #(into [] %))

(when (exists? js/KeyboardEvent)
  (register-keys-printer js/KeyboardEvent 'js/KeyboardEvent [:type :code :key :ctrlKey :altKey :metaKey :shiftKey :isComposing :location :repeat]))

(when (exists? js/TouchEvent)
  (register-keys-printer js/TouchEvent 'js/TouchEvent [:type :altKey :changedTouches :ctrlKey :metaKey :shiftKey :targetTouches :touches])
  (register-keys-printer js/Touch 'js/Touch [:identifier :screenX :screenY :clientX :clientY :pageX :pageY :target])
  (register-printer js/TouchList 'js/TouchList (comp vec seq)))

(register-keys-printer js/PointerEvent 'js/PointerEvent [:type :pointerId :width :height :pressure
                                                         :tangentialPressure :tiltX :tiltY
                                                         :twist :pointerType :isPrimary])

(def mouse-event-keys [:type :altKey :button :buttons :clientX :clientY :ctrlKey :metaKey :movementX :movementY
                       ;; "These are experimental APIs that should not be used in production code" -- MDN
                       ;; :offsetX :offsetY :pageX :pageY
                       :region :relatedTarget :screenX :screenY :shiftKey])

(register-keys-printer js/MouseEvent 'js/MouseEvent mouse-event-keys)
(register-keys-printer js/WheelEvent 'js/WheelEvent (conj mouse-event-keys :deltaX :deltaY :deltaZ :deltaMode))
(register-keys-printer js/DragEvent 'js/DragEvent (conj mouse-event-keys :dataTransfer))

(register-keys-printer js/Blob 'js/Blob [:type :size])
(register-keys-printer js/File 'js/File [:type :size :name :lastModified])
