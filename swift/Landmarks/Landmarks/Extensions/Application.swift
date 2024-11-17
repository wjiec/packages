//
//  Application.swift
//  Landmarks
//
//  Created by Jayson on 2024/11/16.
//

import Foundation
import SwiftUI

extension View {
    
    func hiddenKeyboard() {
        UIApplication.shared.sendAction(
            #selector(UIResponder.resignFirstResponder),
            to: nil, from: nil, for: nil
        )
    }
    
}
