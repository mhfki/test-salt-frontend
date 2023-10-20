package com.konsumen.test.salt.frontend.controller;

import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.konsumen.test.salt.frontend.model.Konsumen;
import com.konsumen.test.salt.frontend.service.KonsumenService;

@Controller
@RequestMapping("/konsumen")
public class KonsumenController {
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	KonsumenService konsumenService;

	@GetMapping("")
	public String getAllKonsumen(Model model, RedirectAttributes redirectAttributes,@RequestParam(required=false) String keyword) {
		Map<String, Object> response = konsumenService.getAllKonsumen(keyword);
		if (response.get("status").equals(HttpStatus.OK.value())) {
			
			model.addAttribute("konsumen", response.get("data"));
		} else {
			redirectAttributes.addFlashAttribute("message", response.get("message"));
		}


		return "index";
	}

	@GetMapping("/create")
	public String register(Model model) {
		return "form-register";
	}

	@PostMapping("/insert")
	public String createKonsumen(Konsumen konsumen, Model model, RedirectAttributes redirectAttributes) {
		Map<String, Object> response = konsumenService.createKonsumen(konsumen);
		if (response.get("status").equals(HttpStatus.CREATED.value())) {

			model.addAttribute("konsumen", konsumen);
			HttpHeaders headers = new HttpHeaders();
			headers.add("Location", "/konsumen/");
			redirectAttributes.addFlashAttribute("success", "Data berhasil ditambahkan");
			return "redirect:/konsumen";

		} else {
			redirectAttributes.addFlashAttribute("error", "Data gagal ditambahkan");
			return "form-register";
		}

	}

	@GetMapping("/edit/{id}")
	public String editKonsumen(@PathVariable("id") int id, Model model) {
		Map<String, Object> response = konsumenService.getKonsumen(id);
		if (response.get("status").equals(HttpStatus.OK.value())) {
			ObjectMapper mapper = new ObjectMapper();
			Konsumen konsumen = mapper.convertValue(response.get("data"), Konsumen.class);
			model.addAttribute("konsumen", konsumen);
		}
		return "form-update";
	}

	@PostMapping("/update/{id}")
	public String updateKonsumen(@PathVariable("id") int id, Konsumen konsumen, Model model,
			RedirectAttributes redirectAttributes) {
		Map<String, Object> response = konsumenService.updateKonsumen(konsumen, id);
		System.out.println(response);
		if (response.get("status").equals(HttpStatus.CREATED.value())) {
			model.addAttribute("konsumen", konsumen);
			redirectAttributes.addFlashAttribute("success", "Data berhasil diubah");

		} else {
			redirectAttributes.addFlashAttribute("error", "Data gagal diubah");
		}

		return "redirect:/konsumen";
	}

	@RequestMapping(value = "/delete/{id}", method = { RequestMethod.DELETE, RequestMethod.GET })
	public String deleteKonsumen(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
		Map<String, Object> response = konsumenService.getKonsumen(id);
		if (response.get("status").equals(HttpStatus.OK.value())) {
			ObjectMapper mapper = new ObjectMapper();
			Konsumen konsumen = mapper.convertValue(response.get("data"), Konsumen.class);
			konsumenService.deleteKonsumen(konsumen, id);
			redirectAttributes.addFlashAttribute("success", "Data berhasil dihapus");

		} else {
			redirectAttributes.addFlashAttribute("error", "Data gagal dihapus");
		}
		return "redirect:/konsumen";

	}

}