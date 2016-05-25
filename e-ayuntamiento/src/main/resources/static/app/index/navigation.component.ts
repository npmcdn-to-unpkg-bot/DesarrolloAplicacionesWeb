import {Component}   from 'angular2/core';
import {ROUTER_DIRECTIVES, RouteParams, Router} from 'angular2/router';

@Component({
    selector: 'navigation-tool',
    directives: [ROUTER_DIRECTIVES],
    templateUrl: 'app/index/navigationTemplate.html',
})

export class NavigationComponent{
    isCollap = true;

}