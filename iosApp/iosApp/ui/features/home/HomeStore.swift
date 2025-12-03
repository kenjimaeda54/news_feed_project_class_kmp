//
//  HomeStore.swift
//  iosApp
//
//  Created by kenjimaeda on 27/11/25.
//
import Shared

class HomeStore: ObservableObject  {
    @Published var state = HomeState()
    private let viewModel: HomeViewModel
    private var cancelJob: Kotlinx_coroutines_coreJob?
    
    init() {
        self.viewModel = HomeViewModel()
        cancelJob = viewModel.cState.subscribe{ newState  in
            if let state = newState {
                DispatchQueue.main.async {
                    self.state = state
                }
            }
        }
        
        
    }
    
    func sendIntent(_ intent: HomeIntent) {
        viewModel.handleIntent(intent: intent)
    }
    
    deinit {
        cancelJob?.cancel(cause: .none)
    }
    
    
}
