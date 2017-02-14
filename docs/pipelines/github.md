# Definition of Github Flow

The Github flow pipeline consists of a number of steps

## Build test publish

 * notify github of status
 * check out source
 * clean build test cycle
 * Deploy package to repository
 * send emails about success/failure
 
 ## Deploy to <env>
 
  * CHeck out chef-repo (using thomascookonline-jenkins creds)
  * run bundle install inside the configured cookbook
  * environment-pin (detailed below)
  * deploy (detailed below)
  * environment-pin (detailed below)
  * (do JIRA updates etc. TODO later once pipeline working)
 
 