package com.kinglloy.iosched.shared.data

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Yalin on 2019-09-02
 */

fun FirebaseFirestore.document2019(): DocumentReference =
    // This is a prefix for Firestore document for 2019
    collection("google_io_event").document("2019")