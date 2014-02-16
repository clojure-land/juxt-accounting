{:jig/components
 {
  :accounts/stencil-loader
  {:jig/component jig.stencil/StencilLoader
   :jig/project #=(eval (str (System/getProperty "user.home") "/src/accounting/project.clj"))}

  :accounts/db
  {:jig/component juxt.accounting.components/Database
   :jig/project #=(eval (str (System/getProperty "user.home") "/src/accounting/project.clj"))
   :db {:uri "datomic:mem://juxt/accounts"}
   }

  :accounts/static-loader
  {:jig/component juxt.accounting.components/StaticLoader
   :jig/project #=(eval (str (System/getProperty "user.home") "/src/accounting/project.clj"))
   :jig/dependencies [:accounts/db]
   ;; Contribute :static-file
   }

  #_:accounts/data-loader
  #_{:jig/component juxt.accounting.components/DataLoader
   :jig/project #=(eval (str (System/getProperty "user.home") "/src/accounting/project.clj"))
   ;; These dependencies can concatenated with DataExtractor components
   :jig/dependencies [:accounts/db]
   }

  :accounts/service
  {:jig/component juxt.accounting.components/Website
   :jig/project #=(eval (str (System/getProperty "user.home") "/src/accounting/project.clj"))
   :jig/dependencies [:accounts/stencil-loader #_:accounts/statement-processor #_:accounts/data-loader]
   :jig.stencil/loader :accounts/stencil-loader

   :juxt.accounting/data :accounts/db

   :bootstrap-dist #=(eval (str (System/getProperty "user.home") "/src/bootstrap/dist"))
   :jquery-dist #=(eval (str (System/getProperty "user.home") "/src/jquery/dist"))
   }

  :accounts/routing
  {:jig/component jig.bidi/Router
   :jig/project #=(eval (str (System/getProperty "user.home") "/src/accounting/project.clj"))
   :jig/dependencies [:accounts/service]
   ;; Optionally, route systems can be mounted on a sub-context
   ;;:jig.web/context "/accounts"
   }

  :accounts/server
  {:jig/component jig.http-kit/Server
   :jig/project #=(eval (str (System/getProperty "user.home") "/src/accounting/project.clj"))
   :jig/dependencies [:accounts/routing]
   :port 8000}

  #_:firefox-reloader
  #_{:jig/component jig.web.firefox-reload/Component
   :jig/dependencies [:accounts/server :console/server :accounts/db #_:accounts/statement-processor]
   :jig.web.firefox-reload/host "localhost"
   :jig.web.firefox-reload/port 32000}}

 }
