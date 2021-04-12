// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

import { HttpHeaders } from "@angular/common/http";

export const environment = {
  production: false,
  server_url:'http://localhost:8080/ecommerce/',
  server_api_url:'http://localhost:8080/ecommerce/rest/json/',
  server_query_url:'http://localhost:8080/ecommerce/rest/json/query/',
  //Basic authorization details
  httpHeaders : new HttpHeaders({'Content-type':'application/json','Authorization': 'Basic ' + btoa('setup' + ':' + 'setup')}),

};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
