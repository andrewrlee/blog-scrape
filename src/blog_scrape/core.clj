(ns blog-scrape.core
   (:gen-class)
   (:require [net.cgrand.enlive-html :as html]
             [clojure.string :only (join)]))
  
  (defn- fetch-page [page]
    (html/html-resource (java.io.StringReader. (slurp page))))
  
  (defn- extract-content [post]
    "Return a map containing title and a seq of the paragraphs as text"
    (let [title       (html/text (first (html/select [post] [:h2])))
          content (map html/text        (html/select [post] [:p]))]
      {:title title, :content content}))
  
  (defn- wrap-as-doc [content]
    (str
      "<html><head><meta charset=\"UTF-8\"></head><body>"
      content
      "</body></html>"))
  
  (defn- convert-to-html [post]
    (str
      "<h1>"
      (:title post)
      "</h1>"
      (clojure.string/join (map #(str "<p>" % "</p>") (:content post)))))

  (def posts 
    (->
      "resources/blog.html"
      (fetch-page)
      (html/select [:div.post])))
  
  (defn -main [& args]
    (->>
      posts
      (reverse)
      (map #(->> % extract-content convert-to-html))
      (clojure.string/join)
      (wrap-as-doc)
      (spit "index.html")))
