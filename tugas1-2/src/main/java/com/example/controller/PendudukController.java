package com.example.controller;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.service.PendudukService;
import com.example.model.PendudukModel;
import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller

public class PendudukController
{
	@Autowired
    PendudukService pendudukDAO;
	boolean nonaktif = false;
	
    @RequestMapping("/")
    public String index ()
    {
        return "index";
    }
    
    @RequestMapping("/penduduk/cari")
    public String cariPenduduk(Model model, @RequestParam(value="kt", required=false) String id_kota, @RequestParam(value="kc", required=false) String id_kecamatan, @RequestParam(value="kl", required=false) String id_kelurahan) {
    	if (id_kota==null && id_kecamatan==null && id_kelurahan==null) {
    		List<KotaModel> kotas = pendudukDAO.getKotas();
        	model.addAttribute("kotas", kotas);
        	return "cari-penduduk-kota";
    	} else if (id_kelurahan==null && id_kecamatan==null) {
    		KotaModel kota = pendudukDAO.getSelectedKota(id_kota);
    		List<KecamatanModel> kecamatans = pendudukDAO.getKecamatans(id_kota);
    		model.addAttribute("kota", kota);
        	model.addAttribute("kecamatans", kecamatans);
        	return "cari-penduduk-kecamatan";
    	} else if (id_kelurahan==null) {
    		KotaModel kota = pendudukDAO.getSelectedKota(id_kota);
    		KecamatanModel kecamatan = pendudukDAO.getSelectedKecamatan(id_kecamatan);
    		log.info(id_kecamatan + " id kecamatan");
    		List<KelurahanModel> kelurahans = pendudukDAO.getKelurahans(id_kecamatan);
    		model.addAttribute("kota", kota);
    		model.addAttribute("kecamatan", kecamatan);
    		model.addAttribute("kelurahans", kelurahans);
    		return "cari-penduduk-kelurahan";
    	} else {
    		KotaModel kota = pendudukDAO.getSelectedKota(id_kota);
    		KecamatanModel kecamatan = pendudukDAO.getSelectedKecamatan(id_kecamatan);
    		log.info(id_kelurahan + " id kelurahan");
    		KelurahanModel kelurahan = pendudukDAO.getSelectedKelurahan(id_kelurahan);
    		List<PendudukModel> penduduks = pendudukDAO.searchForPenduduk(id_kelurahan);
    		for (int i = 0; i < penduduks.size(); i++) {
    			if (penduduks.get(i).getJenis_kelamin().equals("1"))
    				penduduks.get(i).setJenis_kelamin("Perempuan");
    			else
    				penduduks.get(i).setJenis_kelamin("Laki-Laki");
    		}
    		model.addAttribute("kelurahan", kelurahan);
    		model.addAttribute("penduduks", penduduks);
    		model.addAttribute("kota", kota);
    		model.addAttribute("kecamatan", kecamatan);
    		return "penduduk-results";
    	}
    }
        
    @RequestMapping("/penduduk")
    public String getPenduduk(Model model, @RequestParam(value="nik", required=true) String nik) {
    	PendudukModel penduduk = pendudukDAO.getPenduduk(nik);
    	if (penduduk.getIs_wafat().equals("0")) {
    		penduduk.setIs_wafat("Hidup");
    	} else {
    		penduduk.setIs_wafat("Mati");
    	}
    	
    	if (penduduk.getIs_wni().equals("0")) {
    		penduduk.setIs_wni("WNA");
    	} else {
    		penduduk.setIs_wni("WNI");
    	}

    	model.addAttribute("penduduk", penduduk);
    	log.info(nonaktif + " ini true apa false?");
    	if (nonaktif==true) {
    		nonaktif = false;
    		return "success-nonaktif";
    	}
    	
    	return "view-penduduk";
    }
    
    @RequestMapping("/keluarga")
    public String getKeluarga(Model model, @RequestParam(value="nkk", required=true) String nkk) {
    	KeluargaModel keluarga = pendudukDAO.getKeluarga(nkk);
    	
    	for (int i = 0; i < keluarga.getAnggotas().size(); i++) {
    		if (keluarga.getAnggotas().get(i).getJenis_kelamin().equals("0"))
    			keluarga.getAnggotas().get(i).setJenis_kelamin("Laki-Laki");
    		else
    			keluarga.getAnggotas().get(i).setJenis_kelamin("Perempuan");
    		
    		if (keluarga.getAnggotas().get(i).getIs_wni().equals("0")) {
    			keluarga.getAnggotas().get(i).setIs_wni("WNA");
        	} else {
        		keluarga.getAnggotas().get(i).setIs_wni("WNI");
        	}
    	}
    	model.addAttribute("keluarga",keluarga);
    	return "view-keluarga";
    }
    
    @RequestMapping(value = "/penduduk/tambah/", method = RequestMethod.POST)
    public String addPenduduk(@ModelAttribute PendudukModel penduduk, Model model) {
    	KeluargaModel keluarga = pendudukDAO.getKeluargaByID(penduduk.getId_keluarga());
    	String kodeKelurahan = keluarga.getKode_kelurahan().substring(0,6);
    	String tglLahir = penduduk.getTanggal_lahir();
    	String[] ttlSplit = tglLahir.split("-");
		String nik ="";
    	
    	if (penduduk.getJenis_kelamin().equals("perempuan")) { //cewe
    		int tanggalWanita = Integer.parseInt(ttlSplit[2])+40;
    		nik = kodeKelurahan + tanggalWanita + ttlSplit[1] + ttlSplit[0].substring(2, 4);
    	} else {
    	    nik = kodeKelurahan + ttlSplit[2] + ttlSplit[1] + ttlSplit[0].substring(2, 4);
    	    penduduk.setJenis_kelamin("0");
    	}
    	
    	String jumlah = (pendudukDAO.cekNik(nik)+1) + "";
    	if (jumlah.length()==1) {
    		jumlah="000" + jumlah;
    	} else if (jumlah.length()==2) {
    		jumlah="00" + jumlah;
    	} else if (jumlah.length()==3) {
    		jumlah="0" + jumlah;
    	}
    	
		nik += jumlah;
		penduduk.setJenis_kelamin("1");
    	penduduk.setNik(nik);
    	
    	if (penduduk.getIs_wni().equals("WNI")) {
    		penduduk.setWNI("1");
    	} else {
    		penduduk.setWNI("0");
    	}
    	
    	if (penduduk.getIs_wafat().equals("hidup")) {
    		penduduk.setIs_wafat("0");
    	} else {
    		penduduk.setIs_wafat("1");
    	}
    	
    	pendudukDAO.addPenduduk(penduduk);
    	model.addAttribute("penduduk", penduduk);
    	return "success-add";
    }
    
    @RequestMapping(value = "/keluarga/tambah/", method = RequestMethod.POST)
    public String addKeluarga(@ModelAttribute KeluargaModel keluarga, Model model) {
    	String kelurahan = pendudukDAO.getKodeKelurahan(keluarga.getKelurahan()); // kode kelurahan buat nkk
    	String kecamatan = pendudukDAO.getKodeKecamatan(keluarga.getKecamatan()); // kode kecamatan buat nkk
    	String kota = pendudukDAO.getKodeKota(keluarga.getKota()); //kode kota buat nkk
    	DateFormat df = new SimpleDateFormat("ddMMyy");
    	Calendar calobj = Calendar.getInstance();
    	
    	String nkk = kelurahan.substring(0, 6) + df.format(calobj.getTime());
    	keluarga.setKelurahan(pendudukDAO.getIDKelurahan(kelurahan)); //id kelurahan dr kode kelurahan
    	
    	String jumlah = (pendudukDAO.cekNkk(nkk)+1) + "";
    	if (jumlah.length()==1) {
    		jumlah="000" + jumlah;
    	} else if (jumlah.length()==2) {
    		jumlah="00" + jumlah;
    	} else if (jumlah.length()==3) {
    		jumlah="0" + jumlah;
    	}
    	
		nkk += jumlah;
		
    	keluarga.setNomor_kk(nkk);
    	pendudukDAO.addKeluarga(keluarga);
    	model.addAttribute("keluarga", keluarga);
    	return "success-add-keluarga";
    }

    
    @RequestMapping(value = "/penduduk/tambah")
    public String addPenduduk() {
    	return "add-penduduk";
    }
    
    @RequestMapping(value = "/keluarga/tambah")
    public String addKeluarga() {
    	return "add-keluarga";
    }
    
    @RequestMapping(value = "/keluarga/ubah/{nkk}")
    public String editKeluarga(Model model, @PathVariable(value = "nkk") String nkk) {
    	KeluargaModel keluarga = pendudukDAO.getKeluarga(nkk);
    	model.addAttribute("keluarga", keluarga);
    	return "edit-keluarga";
    }
    
    @RequestMapping(value = "/penduduk/mati")
    public String nonaktifPenduduk(@ModelAttribute PendudukModel penduduk, Model model) {
    	pendudukDAO.updateStatusKematian(penduduk);
    	penduduk = pendudukDAO.getPenduduk(penduduk.getNik());
    	nonaktif = true;
    	KeluargaModel keluarga = pendudukDAO.getKeluargaByID(penduduk.getId_keluarga());
    	KeluargaModel keluarga2 = pendudukDAO.getKeluarga(keluarga.getNomor_kk());
    	List<PendudukModel> anggota = keluarga2.getAnggotas();
    	int jumlahMeninggal = 0;
    	
    	for (int i = 0; i < anggota.size(); i++) {
    		if (anggota.get(i).getIs_wafat().equals("1")) {
    			jumlahMeninggal++;
    		}
    	}
    	
    	if (jumlahMeninggal==anggota.size()) {
    		pendudukDAO.nonaktifKeluarga(keluarga.getNomor_kk());
    	}
    	
    	return "redirect:/penduduk?nik="+penduduk.getNik();
    }
    
    @RequestMapping(value = "/keluarga/ubah/{nkk}", method = RequestMethod.POST)
    public String editKeluarga(@ModelAttribute KeluargaModel keluarga, Model model, @PathVariable(value = "nkk") String nkkLama) {
    	keluarga.setNkkLama(nkkLama);
    	String kodeKelurahan = pendudukDAO.getKodeKelurahan(keluarga.getKelurahan()); // kode kelurahan 
    	DateFormat df = new SimpleDateFormat("ddMMyy");
    	Calendar calobj = Calendar.getInstance();
    	
    	if (!(kodeKelurahan.substring(0,6).equals(nkkLama.substring(0,6)))) {
        	String nkk = kodeKelurahan.substring(0, 6) + df.format(calobj.getTime());
        	String jumlah = (pendudukDAO.cekNkk(nkk)+1) + "";
        	if (jumlah.length()==1) {
        		jumlah="000" + jumlah;
        	} else if (jumlah.length()==2) {
        		jumlah="00" + jumlah;
        	} else if (jumlah.length()==3) {
        		jumlah="0" + jumlah;
        	}
        	
    		nkk += jumlah;   		
        	keluarga.setNomor_kk(nkk);
    	} else {
    		String nkk = nkkLama.substring(0,6) + df.format(calobj.getTime()) + nkkLama.substring(12);
    		keluarga.setNomor_kk(nkk);
    	}
    	keluarga.setKelurahan(pendudukDAO.getIDKelurahan(kodeKelurahan)); //id kelurahan dr kode kelurahan
    	pendudukDAO.updateKeluarga(keluarga);
    	model.addAttribute("keluarga", keluarga);
    	return "success-edit-keluarga";
    }
    
    @RequestMapping("/penduduk/ubah/{nik}")
    public String editPenduduk (Model model, @PathVariable(value = "nik") String nik)
    {
    	PendudukModel penduduk = pendudukDAO.getPenduduk(nik);
    	model.addAttribute("penduduk", penduduk);
    	return "edit-penduduk";
    }
    
    @RequestMapping(value = "/penduduk/ubah/{nik}", method = RequestMethod.POST)
    public String editPendudukSubmit(@ModelAttribute PendudukModel penduduk, Model model, @PathVariable(value = "nik") String nikLama) {
    	penduduk.setNikLama(nikLama);
    	KeluargaModel keluarga = pendudukDAO.getKeluargaByID(penduduk.getId_keluarga());
    	String kodeKelurahan = keluarga.getKode_kelurahan().substring(0,6);
    	String tglLahir = penduduk.getTanggal_lahir();
    	String[] ttlSplit = tglLahir.split("-");
		String nik ="";
    	
    	if (penduduk.getJenis_kelamin().equals("perempuan")) { //cewe
    		int tanggalWanita = Integer.parseInt(ttlSplit[2])+40;
    		nik = kodeKelurahan + tanggalWanita + ttlSplit[1] + ttlSplit[0].substring(2, 4);
    	} else {
    	    nik = kodeKelurahan + ttlSplit[2] + ttlSplit[1] + ttlSplit[0].substring(2, 4);
    	    penduduk.setJenis_kelamin("0");
    	}
    	
    	String jumlah = (pendudukDAO.cekNik(nik)+1) + "";
    	if (jumlah.length()==1) {
    		jumlah="000" + jumlah;
    	} else if (jumlah.length()==2) {
    		jumlah="00" + jumlah;
    	} else if (jumlah.length()==3) {
    		jumlah="0" + jumlah;
    	}
    	
		nik += jumlah;
		
		if (!(nikLama.substring(0, 12).equals(nik.substring(0, 12)))) 
		penduduk.setNik(nik);
		
		penduduk.setJenis_kelamin("1");
    	
    	if (penduduk.getIs_wni().equals("WNI")) {
    		penduduk.setWNI("1");
    	} else {
    		penduduk.setWNI("0");
    	}
    	
    	if (penduduk.getIs_wafat().equals("hidup")) {
    		penduduk.setIs_wafat("0");
    	} else {
    		penduduk.setIs_wafat("1");
    	}
    	
    	pendudukDAO.updatePenduduk(penduduk);
    	model.addAttribute("penduduk", penduduk);
    	return "success-edit-penduduk";
    }
}
