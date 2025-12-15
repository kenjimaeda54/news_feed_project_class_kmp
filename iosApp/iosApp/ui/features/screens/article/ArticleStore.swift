//
//  HomeStore.swift
//  iosApp
//
//  Created by kenjimaeda on 27/11/25.
//
import Shared

class ArticleStore: ObservableObject  {
    @Published var state = ArticleState()
    private let viewModel: ArticleViewModel
    private var cancelJob: Kotlinx_coroutines_coreJob?
    
    init() {
        self.viewModel = ArticleViewModel()
        cancelJob = viewModel.cState.subscribe{ newState  in
            if let state = newState {
                DispatchQueue.main.async {
                    self.state = state
                }
            }
        }
        
        
    }
    
    func sendIntent(_ intent: ArticleIntent) {
        viewModel.handleIntent(intent: intent)
    }
    
    deinit {
        cancelJob?.cancel(cause: .none)
    }
    
    
}
