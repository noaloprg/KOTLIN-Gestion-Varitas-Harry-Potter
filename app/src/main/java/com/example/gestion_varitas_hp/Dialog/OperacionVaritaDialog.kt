package com.example.gestion_varitas_hp.Dialog

import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class OperacionVaritaDialog : DialogFragment() {

    companion object {
        val CLAVE = "operacion_varita"
        val CLAVE_RESULTADO = "resultado_operacion_varita"
        val TITULO = "arg_titulo"
        val DESCRIPCION = "arg_descripcion"

        val BTN_POS = "Aceptar"

        fun newInstance(mensaje: String, titulo: String): OperacionVaritaDialog {
            return OperacionVaritaDialog().apply {
                arguments = bundleOf(
                    DESCRIPCION to mensaje,
                    TITULO to titulo
                )
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val mensaje = requireArguments().getString(DESCRIPCION)
        val titulo = requireArguments().getString(TITULO)

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(titulo)
            .setMessage(mensaje)
            .setPositiveButton(BTN_POS) { dialog, which ->
                setFragmentResult(CLAVE, bundleOf(CLAVE_RESULTADO to true))
            }
            .create()
    }
}