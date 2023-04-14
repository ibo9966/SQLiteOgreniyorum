package com.example.sqlogreniyorum2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sqlogreniyorum2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val context = this
        var db = DataBaseHelper(context)

        binding.btnkaydet.setOnClickListener {
            var etadsoyad = binding.etadsoyad.text.toString()
            var etyas = binding.etyas.text.toString()
            var etmemleket = binding.etmemleket.text.toString()
            var ettelefon = binding.ettelefon.text.toString()

            if (etadsoyad.isNotEmpty() && etyas.isNotEmpty()){
                val user = User(etadsoyad,etyas.toInt(),etmemleket,ettelefon)
                db.insertData(user)
            }else{
                Toast.makeText(this,"Lütfen boş alanları doldurunuz",Toast.LENGTH_SHORT).show()
            }
        }
        //Verileri okumak için
        binding.btnoku.setOnClickListener {
            var data = db.readData()
            binding.tvSonuc.text = ""
            for (i in 0 until data.size){
                binding.tvSonuc.append(
                    data.get(i).id.toString()+" - "
                        +data.get(i).adsoyad+ " - "
                            + data.get(i).yas + " - "
                            + data.get(i).memleket + " - "
                            + data.get(i).tel+ " \n")
            }
        }

        //Verileri güncelleme için
        binding.btnguncelle.setOnClickListener {
            db.updateData()
            binding.btnoku.performClick()
        }

        //Verileri silmek içim
        binding.btnsil.setOnClickListener {
            db.deleteData()
            binding.btnoku.performClick()
        }
    }
}