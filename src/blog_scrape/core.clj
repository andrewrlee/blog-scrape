(ns blog-scrape.core
   (:gen-class)
   (:require [net.cgrand.enlive-html :as html]
             [clojure.string :only (join)]))
  
  (defn- extract-content [post]
    (let [title       (html/text (first (html/select [post] [:h2])))
          content (map html/text        (html/select [post] [:p]))]
      {:title title, :content content}))
  
  (defn- convert-to-html [post]
    (str
      "<h1>"
      (:title post)
      "</h1>"
      (clojure.string/join (map #(str "<p>" % "</p>") (:content post)))))

  (defn- wrap-as-doc [content]
    (str
      "<html><head><meta charset=\"UTF-8\"></head><body>"
      content
      "</body></html>"))

  (def posts 
    (->
      "resources/blog.html"
      (slurp)
      (java.io.StringReader.)
      (html/html-resource)
      (html/select [:div.post])))
  
  (defn -main [& args]
    (->>
      posts
      (reverse)
      (map #(comp convert-to-html extract-content))
      (clojure.string/join)
      (wrap-as-doc)
      (spit "index.html")))
