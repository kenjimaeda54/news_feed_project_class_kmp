//
//  BaseStore.swift
//  iosApp
//
//  Created by kenjimaeda on 26/12/25.
//

import Shared

class BaseStore<State:AnyObject,Intent>: ObservableObject  {
    @Published var state: State
    private var cancelJob: Kotlinx_coroutines_coreJob?
    private let intentHandler: (Intent) -> Void
    
    init(
        initialState: State,
        subscribe: (@escaping (State) -> Void) -> Kotlinx_coroutines_coreJob,
        handleIntent: @escaping (Intent) -> Void
    ) {
        self.state = initialState
        self.intentHandler = handleIntent
        
        
        self.cancelJob = subscribe{ [weak self] newState  in
                DispatchQueue.main.async {
                    self?.state =  newState
            }
        }
        
        
    }
    
    func sendIntent(_ intent: Intent) {
        intentHandler(intent)
    }
    
    deinit {
        cancelJob?.cancel(cause: .none)
    }
    
    
}
