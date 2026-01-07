//
//  StateFactory.swift
//  iosApp
//
//  Created by kenjimaeda on 26/12/25.
//
import Shared

class StateFactory {
    static func makeArticleStore() -> BaseStore<ArticleState, ArticleIntent> {
        let vm = ArticleViewModel()
        return BaseStore(
            initialState: vm.initState,
            subscribe: vm.subscribe,
            handleIntent: vm.handleIntent
        )
    }
    
     static func makeHomeStore() -> BaseStore<HomeState, HomeIntent> {
        let vm = HomeViewModel()
        return BaseStore(
            initialState: vm.initState,
            subscribe: vm.subscribe,
            handleIntent: vm.handleIntent
        )
    }
    

}
