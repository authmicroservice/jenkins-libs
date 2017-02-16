# environment-pin

This is an analysis of what goes on when pinning environments using Chef

## inputa

This phase needs to be aware of the app name ${APP_NAME}, Chef environment and desired version to pin, also known as APP_SPEC.

This is computed differently per-platform. 

## Steps

 * verify required environment (integration|QA|staging|production) exists
 * using knife exec, set env.default_attributes['apps'][$APP_NAME] to the required APP_SPEC
 * double-check that it worked, just in case a phjantom write occurred by another job
 


