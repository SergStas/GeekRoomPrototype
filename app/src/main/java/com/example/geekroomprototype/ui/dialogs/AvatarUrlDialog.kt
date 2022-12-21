package com.example.geekroomprototype.ui.dialogs

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.Window
import com.example.geekroomprototype.databinding.DialogAvatarBinding
import com.example.geekroomprototype.util.extensions.toast

class AvatarUrlDialog(
    private val onChange: (String) -> Unit,
    private val oldValue: String,
    activity: Activity,
): Dialog(activity) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val binding = DialogAvatarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.run {
            etUrl.setText(oldValue)
            bSubmit.setOnClickListener {
                onChange(etUrl.text.toString())
                dismiss()
                context.toast("Avatar updated")
            }
        }
    }
}