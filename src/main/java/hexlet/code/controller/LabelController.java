package hexlet.code.controller;

import hexlet.code.dto.LabelDto;
import hexlet.code.model.Label;
import hexlet.code.repository.LabelRepository;
import hexlet.code.service.LabelServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.NoSuchElementException;

import static hexlet.code.controller.LabelController.LABEL_CONTROLLER_PATH;
import static hexlet.code.controller.UserController.ID;

@RestController
@RequestMapping("${base-url}" + LABEL_CONTROLLER_PATH)
@AllArgsConstructor
public class LabelController {

    public static final String LABEL_CONTROLLER_PATH = "/labels";

    @Autowired
    private LabelServiceImpl labelService;

    @Autowired
    private LabelRepository labelRepository;

    @GetMapping(ID)
    public Label getLabel(@PathVariable long id) {
        return labelRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No label with current id"));
    }

    @PutMapping(ID)
    public Label updateLabel(
            @PathVariable long id,
            @RequestBody LabelDto labelDto
    ) {
        return labelService.updateLabel(id, labelDto);
    }

    @DeleteMapping(ID)
    public void deleteLabel(@PathVariable long id) {
        labelService.deleteLabel(id);
    }

    @GetMapping
    public List<Label> getLabels() {
        return labelRepository.findAll();
    }

    @PostMapping
    public Label createLabel(@RequestBody LabelDto labelDto) {
        return labelService.createLabel(labelDto);
    }
}
